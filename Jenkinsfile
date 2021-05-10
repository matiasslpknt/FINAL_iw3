pipeline {
    agent any

    stages {
        stage('Install') {
            steps {
                echo 'INSTALLING...'
                mvn install
            }
        }
        stage('Test') {
            steps {
                echo 'TESTING...'
                mvn test
            }
        }
        stage('Deploying') {
            steps {
                echo 'DEPLOYING...'
                mvn spring-boot:run -Dspring-boot.run.profiles=mysql
            }
        }
    }
}