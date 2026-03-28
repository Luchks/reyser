<?php
    declare(strict_types=1);

    require_once __DIR__ . '/../vendor/autoload.php';


    $dotenv = Dotenv\Dotenv::createImmutable(__DIR__ . '/../');
    $dotenv->load();


    use App\Routes\Router;
    use App\Services\AuthMiddleware;

    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS");
    header("Access-Control-Allow-Headers: Content-Type, Authorization");

    if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
        http_response_code(200);
        exit;
    }

    $router = new Router();

    // Rutas Públicas
    $router->add('GET', '/usuarios', 'UserController@index');
    $router->add('POST', '/usuarios', 'UserController@store', AuthMiddleware::class);
    $router->add('DELETE', '/usuarios/{id}', 'UserController@delete', AuthMiddleware::class);

    try {
        $router->dispatch();
    } catch (\Throwable $e) {
        error_log($e->getMessage());
        http_response_code(500);
        echo json_encode([
            "status" => "error",
            "message" => "Error interno del servidor",
            "debug" => $e->getMessage() 
        ]);
    }
