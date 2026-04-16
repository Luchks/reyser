#!/bin/bash

# Nombre de la carpeta raíz
PROYECTO="mi_api"

# Verificar si el directorio ya existe
if [ -d "$PROYECTO" ]; then
    echo "⚠️ La carpeta '$PROYECTO' ya existe. Abortando para evitar sobrescrituras."
    exit 1
fi

echo "🚀 Iniciando creación de la estructura para: $PROYECTO"

# Crear estructura de directorios
mkdir -p $PROYECTO/{vendor,public,src/{Config,Controllers,Models,Services,Repositories,Routes,Helpers},logs}

# Crear archivos base (vacíos)
touch $PROYECTO/composer.json
touch $PROYECTO/composer.lock
touch $PROYECTO/public/index.php
touch $PROYECTO/.env
touch $PROYECTO/.htaccess

# Dar permisos de escritura a logs (opcional pero recomendado)
chmod 775 $PROYECTO/logs

echo "✅ Estructura creada con éxito."
echo "📂 Directorio actual:"
ls -R $PROYECTO
