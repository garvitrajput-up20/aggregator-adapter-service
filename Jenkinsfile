pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/garvitrajput-up20/aggregator-adapter-service.git'
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
    }

    post {
        success {
            echo 'Build succeeded'
        }
        failure {
            echo 'Build failed'
        }
    }
}
