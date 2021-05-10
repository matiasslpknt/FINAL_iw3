pipeline {
    agent any
    tools {
        maven 'iw3_maven'
        jdk 'iw3_jdk'
        docker 'iw3_docker'
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
                sh "docker-compose build"
                sh "docker-compose up -d"
            }
        }
    }
}