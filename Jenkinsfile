pipeline {
    agent any
    options {
        timeout(time: 150, unit: 'MINUTES')
    }
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
