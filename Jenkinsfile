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
                sh "docker run -p 3307:3306 --name=mysql -d mysql:latest"
                sh "docker run -p 8081:8080 --name=iw3 -d matiasslpknt1/iw3:0.0.6.RELEASE"
                sh "docker build -t matiasslpknt1/iw3:0.0.6.RELEASE ."
                sh "docker push matiasslpknt1/iw3:0.0.6.RELEASE"
            }
        }
    }
}