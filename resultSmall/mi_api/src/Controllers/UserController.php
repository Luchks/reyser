<?php
    namespace App\Controllers;

    use App\Repositories\UserRepository;
    use App\Helpers\ResponseHelper;
    use App\Config\Database;

    class UserController {
        private $repository;

        public function __construct() {
            $database = new Database();
            $db = $database->getConnection();
            $this->repository = new UserRepository($db);
        }

        public function index() {
            $users = $this->repository->getAll();
            ResponseHelper::json($users, 200, "Lista de usuarios obtenida");
        }

        public function store() {
            $data = json_decode(file_get_contents("php://input"), true);
            
            if (empty($data['nombre']) || empty($data['email'])) {
                ResponseHelper::json(null, 400, "Nombre y email son obligatorios");
            }

            $userId = $this->repository->create($data);

            if ($userId) {
                $data['id'] = $userId;
                ResponseHelper::json($data, 201, "Usuario creado correctamente");
            } else {
                ResponseHelper::json(null, 500, "Error interno al crear el usuario");
            }
        }

        public function delete($id) {
            if ($this->repository->delete($id)) {
                ResponseHelper::json(null, 200, "Usuario con ID $id eliminado correctamente");
            } else {
                ResponseHelper::json(null, 500, "No se pudo eliminar al usuario");
            }
        }


    }
