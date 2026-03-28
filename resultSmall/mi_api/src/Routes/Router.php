<?php
    namespace App\Routes;

    class Router {
        protected $routes = [];

        public function add(string $method, string $path, string $handler, $middleware = null): void {
            $this->routes[] = [
                'method'     => strtoupper($method),
                'path'       => $path,
                'handler'    => $handler,
                'middleware' => $middleware
            ];
        }

        public function dispatch(): void {
            $uri = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH);
            $method = $_SERVER['REQUEST_METHOD'];

            foreach ($this->routes as $route) {
                // Convertimos la ruta dinámica /usuarios/{id} en una expresión regular
                $pattern = preg_replace('/\{([a-zA-Z0-9_]+)\}/', '([a-zA-Z0-9_]+)', $route['path']);
                $pattern = "#^" . $pattern . "$#";

                if ($route['method'] === $method && preg_match($pattern, $uri, $matches)) {
                    // Quitamos el primer elemento (que es la ruta completa) para quedarnos con los IDs
                    array_shift($matches);

                    if ($route['middleware']) {
                        call_user_func([$route['middleware'], 'handle']);
                    }

                    $this->executeHandler($route['handler'], $matches);
                    return;
                }
            }

            http_response_code(404);
            echo json_encode(["status" => "error", "message" => "Ruta no encontrada"]);
        }

        protected function executeHandler(string $handler, array $params): void {
            list($controllerName, $method) = explode('@', $handler);
            $fullControllerName = "App\\Controllers\\" . $controllerName;

            if (class_exists($fullControllerName)) {
                $controller = new $fullControllerName();
                // Pasamos los parámetros (como el ID) al método del controlador
                call_user_func_array([$controller, $method], $params);
            } else {
                throw new \Exception("Controlador $controllerName no encontrado");
            }
        }

    }
