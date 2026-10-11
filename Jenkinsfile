
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Login Azure VM') {
            steps {
                echo 'Testing SSH connection to Azure VM'
                sshagent(credentials: ['azure-vm-dev-01']) {
                    sh '''
                       ssh -o BatchMode=yes \
                           -o ConnectTimeout=10 \
                           azureuser@20.41.232.96 \
                           "hostname"
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
