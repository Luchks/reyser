<?php
    namespace App\Repositories;

    use PDO;

    class UserRepository {
        private $db;

        public function __construct(PDO $db) {
            $this->db = $db;
        }

        public function getAll(): array {
            $query = "SELECT id, nombre, email FROM usuarios";
            $stmt = $this->db->prepare($query);
            $stmt->execute();
            return $stmt->fetchAll(PDO::FETCH_ASSOC) ?: [];
        }

        public function create(array $data) {
            $query = "INSERT INTO usuarios (nombre, email) VALUES (:nombre, :email)";
            $stmt = $this->db->prepare($query);
            
            $success = $stmt->execute([
                ':nombre' => $data['nombre'],
                ':email'  => $data['email']
            ]);

            return $success ? $this->db->lastInsertId() : false;
        }

        public function delete($id): bool {
            $query = "DELETE FROM usuarios WHERE id = :id";
            $stmt = $this->db->prepare($query);
            return $stmt->execute([':id' => $id]);
        }
    }
