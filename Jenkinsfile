
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Deploy to Azure VM') {
            steps {
                sshagent(credentials: ['azure-vm-dev-01']) {
                    sh '''
                       scp index.html azureuser@20.41.232.96:/tmp/index.html
                       ssh azureuser@20.41.232.96 "sudo cp /tmp/index.html /var/www/html/index.html"
                    '''
                }
            }
        }
        
    }

    post {
        success {
            echo 'success'
        }
        failure {
            echo 'failure'
        }
    }
}
