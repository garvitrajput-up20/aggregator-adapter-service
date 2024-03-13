pipeline {
    agent any

    stages {
        
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
