def call () {
  sh "echo IM FROM SHARED LIB"
}

def push_changes_to_master () {
    dir("${WORKSPACE}") {
      withCredentials([sshUserPrivateKey(credentialsId:'jenkins-master-git-key', keyFileVariable: 'SSH_KEY', usernameVariable: 'SSH_USER')]) {
        withEnv(["GIT_SSH_COMMAND=ssh -o StrictHostKeyChecking=no -o User=${SSH_USER} -i ${SSH_KEY}"]){
          sh '''
            git config --local user.name "jenkins"
            git config --local user.email "dddsd@erf.com"
            git add .
            git commit -m "commit from Jenkins"
            git push origin service_branch
          '''
          //sh 'git push origin cert_update_branch'
        }
//        sh '''
//          git config --local user.name "jenkins"
//          git config --local user.email "jenkins@jenkins.com"
//          git checkout cert_update_branch
//          git add .
//          git commit -m "HCSINFRA-4593 commit from Jenkins"
//          export GIT_SSH_COMMAND = "ssh -i $PRIVATE_KEY"
//          git push origin cert_update_branch
//        '''
    }
  }
}


def ansible_fake (ansible_limit, inventory) {
  sh """
    echo "Start playbook -l ${ansible_limit} -i ${inventory}"
  """
}
