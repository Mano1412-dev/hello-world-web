
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
                    grep -qi 'Hello World' index.html

                    echo "HTML validation successful"
                '''
            }
        }

        stage('Deploy to Azure VM') {
            steps {
                sshagent(credentials: ['azure-vm-deploy-key']) {
                    sh '''
                        set -eu

                        SSH_OPTS="-o BatchMode=yes -o StrictHostKeyChecking=yes"

                        scp $SSH_OPTS index.html \
                          "$VM_USER@$VM_HOST:/tmp/index.html"

                        ssh $SSH_OPTS "$VM_USER@$VM_HOST" \
                          'sudo install -o root -g webdeploy -m 0644 /tmp/index.html /var/www/html/index.html && sudo nginx -t && sudo systemctl reload nginx && rm -f /tmp/index.html'

                        echo "Deployment successful"
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'Hello World website deployed successfully.'
        }

        failure {
            echo 'Deployment failed. Review the stage logs.'
        }
    }
}
