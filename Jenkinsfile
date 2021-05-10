pipeline {
    agent any

    stages {
        stage('Install') {
            steps {
                echo 'INSTALLING...'
                sh "mvn install"
            }
        }
        stage('Test') {
            steps {
                echo 'TESTING...'
                sh "mvn test"
            }
        }
        stage('Deploying') {
            steps {
                echo 'DEPLOYING...'
                sh "sudo docker-compose build"
                sh "sudo docker-compose up -d"
            }
        }
    }
}