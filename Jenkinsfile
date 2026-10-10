
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Deploy') {
            steps {
                sshagent(credentials: ['azure-vm-deploy-key']) {
                    sh '''
                        scp index.html azureuser@20.41.232.96:/tmp/index.html

                        ssh azureuser@20.41.232.96 \
                          "sudo cp /tmp/index.html /var/www/html/index.html"
                    '''
                }
            }
        }
    }
}
