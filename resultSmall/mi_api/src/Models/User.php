<?php
    namespace App\Models;

    class User {
        public $id;
        public $nombre;
        public $email;

        public function __construct($id = null, $nombre = null, $email = null) {
            $this->id = $id;
            $this->nombre = $nombre;
            $this->email = $email;
        }
    }
