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
                sh "docker-compose build"
                sh "docker-compose up -d"
            }
        }
    }
}