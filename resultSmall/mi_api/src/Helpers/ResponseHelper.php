<?php
    namespace App\Helpers;

    class ResponseHelper {
        public static function json($data = null, int $status = 200, string $message = null) {
            if (ob_get_length()) ob_clean();

            http_response_code($status);
            
            $response = [
                "status"  => $status < 400 ? "success" : "error",
                "code"    => $status,
                "message" => $message,
                "data"    => $data
            ];

            echo json_encode($response, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
            exit; 
        }
    }
