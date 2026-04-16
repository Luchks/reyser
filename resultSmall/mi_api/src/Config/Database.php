<?php
    namespace App\Config;

    use PDO;
    use PDOException;

    class Database {
        private $host;
        private $db_name;
        private $username;
        private $password;
        public $conn;

        public function __construct() {
            // Leemos los valores del archivo .env
            $this->host = $_ENV['DB_HOST'] ?? 'localhost';
            $this->db_name = $_ENV['DB_NAME'] ?? '';
            $this->username = $_ENV['DB_USER'] ?? '';
            $this->password = $_ENV['DB_PASS'] ?? '';
        }

        public function getConnection() {
            $this->conn = null;
            try {
                $this->conn = new PDO(
                    "mysql:host=" . $this->host . ";dbname=" . $this->db_name,
                    $this->username,
                    $this->password
                );
                // ... resto del método original
            } catch(PDOException $exception) {
                throw new \Exception("Error de conexión: " . $exception->getMessage());
            }
            return $this->conn;
        }
    }
