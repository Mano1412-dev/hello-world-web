pipeline {
    agent any
    options {
        timestamps()
    }
    environment {
        VM_HOST = '20.41.232.96'
        VM_USER = 'azureuser'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Validate HTML') {
            steps {
                sh '''
                   set -eu
                   test -s index.html
                   grep -qi '<html' index.html
                   grep -qi 'Hello world' index.html
                   echo "HTML validation success"
                '''
            }
        }
        stage(Deploy to Azure) {
            steps {
                sshagent(credentials: ['azure-vm-deploy-key']) {
                    sh '''
                       scp index.html "$VM_USER@$VM_HOST:/var/www/html/index.html"
                       ssh "$VM_USER@$VM_HOST" 'sudo nginx -t && sudo systemctl reload nginx'
                       echo "Deployment successful"
                    '''
                }
            }
        }
    }
    post {
        success {
            echo "success"
        }
        failure {
            echo "failure"
        }
    }
}