<?php
    namespace App\Services;

    use App\Helpers\ResponseHelper;

    class AuthMiddleware {
        public static function handle() {
            $headers = getallheaders();
            $authHeader = $headers['Authorization'] ?? $headers['authorization'] ?? null;

            if (!$authHeader || !preg_match('/Bearer\s(\S+)/', $authHeader, $matches)) {
                ResponseHelper::json(null, 401, "Token no proporcionado o formato inválido");
            }

            $token = $matches[1];

            if ($token !== $_ENV['AUTH_TOKEN']) {
                ResponseHelper::json(null, 401, "Acceso denegado: Token inválido");
            }

        }
    }

