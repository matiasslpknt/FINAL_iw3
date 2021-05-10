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
                sh "docker-compose -f docker-compose.yml up"
            }
        }
    }
}