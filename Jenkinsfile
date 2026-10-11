
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
                sshagent(credentials: ['azure-vm-deploy-key']) {
                    sh '''
                       ssh -o BatchMode=yes \
                           -o ConnectTimeout=10 \
                           azureuser@10.20.1.4 \
                           "hostname"
                    '''
                }
            }
        }

        stage('Deploy') {
            steps {
               sshagent(credentials: ['azure-vm-deploy-key']) {
                   sh '''
                      set -e
                      echo 'Starting deployment'

                      scp index.html azureuser@10.20.1.4:/tmp/index.html

                      ssh azureuser@10.20.1.4 \
                          "sudo cp /tmp/index.html /var/www/html/index.html"

                      echo 'Deployment successful'
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
