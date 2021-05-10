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
                docker-compose build
                docker-compose up -d
            }
        }
    }
}