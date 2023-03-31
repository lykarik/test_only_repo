pipeline {
  agent {label 'master'}

  stages {
    stage ('Some commands') {
      steps {
        sh "pwd"
        sh "ls -la"
        sh "git branch && git status"
      }
    }
    stage ('Git checkout main branch') {
      steps {
        dir("$WORKSPACE") {
        checkout([$class: 'GitSCM',
                  userRemoteConfigs: [[url: "git@github.com:lykarik/test_only_repo.git",
                  credentialsId: 'jenkins-master-git-key']],
                  branches: [[name: "main"]],
                  extensions: [
                    [$class: 'CloneOption',
                      reference: "git@github.com:lykarik/test_only_repo.git",
                      shallow: true,
                      depth: "1"]
                  ]
        ])
        }
      }
    }
    stage ('Move to service branch') {
      steps {
        dir("$WORKSPACE") {
          sh "git checkout service_branch"
        }
      }
    }
    stage ('Change repo') {
      steps {  
        dir("$WORKSPACE") {
          sh """
             touch file_1
             echo "asd" >> file_1
          """
        }
      }
    }
//    stage ('Create merge request') {
//      steps {
//        dir("$WORKSPACE") {
//        checkout([$class: 'GitSCM',
//                  userRemoteConfigs: [[url: "git@github.com:lykarik/test_only_repo.git",
//                  credentialsId: 'jenkins-master-git-key']],
//                  extensions: [
//                    [$class: 'PreBuildMerge',
//                      options: [ mergeTarget: "main", fastForwardMode: "FF", mergeRemote: "origin" ]
//                    ]
//                  ]
//        ])
//        }
//      }
//    }
  }

post {
  success {
    sshagent(['jenkins-master-git-key']) {
      sh """
        git config --local user.name "jenkins"
        git config --local user.email "dddsd@erf.com"
        git add .
        git commit -m "commit from Jenkins"
        git push origin service_branch
      """
    }
  }
  failure {
      echo "1"
  }
  aborted {
    echo "13"
  }
}
}
