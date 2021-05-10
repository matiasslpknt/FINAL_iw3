pipeline {
    agent any
    tools {
        maven 'iw3_maven'
        jdk 'iw3_jdk'
        dockerTool 'iw3_docker'
    }

    stages {
        stage('Install') {
            steps {
                echo 'INSTALLING...'
                sh "mvn install"
            }
        }
        stage('Deploying') {
            steps {
                echo 'DEPLOYING...'
                sh "docker run -p --name=mysql 3307:3306 -d mysql:latest"
                sh "docker run -p --name=iw3 8081:8080 -d matiasslpknt1/iw3:0.0.6.RELEASE"
                sh "docker build -t --name=iw3 matiasslpknt1/iw3:0.0.6.RELEASE ."
                sh "docker push matiasslpknt1/iw3:0.0.6.RELEASE"
            }
        }
    }
}