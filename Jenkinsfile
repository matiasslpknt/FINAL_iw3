pipeline {
    agent any
    tools {
        maven 'Maven 3.8.1'
        jdk 'jdk8'
    }
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