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
                sh "docker system prune"
                sh "docker build -t matiasslpknt1/iw3:0.0.6.RELEASE ."
                sh "docker login -u matiasslpknt08@gmail.com -p Mati.3269"
                sh "docker push matiasslpknt1/iw3:0.0.6.RELEASE"
            }
        }
    }
}