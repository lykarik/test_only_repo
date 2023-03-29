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
          sh "git checkout -b service_branch"
        }
      }
    }
    stage ('Change repo') {
      steps {  
        dir("$WORKSPACE") {
          sh "touch file_1"
          sh "echo "asd" >> file_1"
        }
      }
    }
    stage ('Create merge request') {
      steps {
        dir("$WORKSPACE") {
        checkout([$class: 'GitSCM',
                  userRemoteConfigs: [[url: "git@github.com:lykarik/test_only_repo.git",
                  credentialsId: 'jenkins-master-git-key']],
                  extensions: [
                    [$class: 'PreBuildMerge',
                      mergeTarget: "main",
                      fastForwardMode: FF,
                      mergeRemote: "origin"
                     ]
                  ]
        ])
        }
      }
    }
  }
}
