pipeline {
    agent any
    
    environment {
        MAVEN_CMD = 'C:/Program Files/JetBrains/IntelliJ IDEA 2026.2.3/plugins/maven-plugin/lib/maven3/bin/mvn.cmd'
        JAVA_HOME = 'C:/Users/37544/AppData/Local/Programs/Microsoft/jdk-25.0.4.101-hotspot'
    }

    stages {
        stage('Build') {
            steps {
                echo 'Начинаем компиляцию проекта...'
                bat "\"${env.MAVEN_CMD}\" clean compile"
            }
        }
        stage('Test') {
            steps {
                echo 'Запускаем модульные тесты...'
                bat "\"${env.MAVEN_CMD}\" test"
            }
        }
        stage('Package') {
            steps {
                echo 'Упаковываем проект в JAR-файл...'
                bat "\"${env.MAVEN_CMD}\" package -DskipTests"
            }
        }
        stage('Inspect Target') {
            steps {
                echo 'Проверяем содержимое директории target...'
                bat 'dir target'
            }
        }
    }
}