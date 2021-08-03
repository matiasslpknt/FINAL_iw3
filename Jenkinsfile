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
                sh "rm -rf target && mvn -Dmaven.test.skip=true -Dbuild=jar package && cp target/iw3.jar docker-config/spring"
            }
        }
        stage('Deploy') {
            steps {
                echo 'DEPLOYING...'
                sh "docker build -t matiasslpknt1/iw3:0.0.6.RELEASE ."
                sh "docker push matiasslpknt1/iw3:0.0.6.RELEASE"
            }
        }
    }
}