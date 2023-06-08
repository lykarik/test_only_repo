library identifier: 'test_only_repo@jenkins_shared',
        retriever: modernSCM([
          $class: 'GitSCMSource',
          credentialsId: 'jenkins-master-git-key',
          remote: 'git@github.com:lykarik/test_only_repo.git'])

pipeline {
  agent {label 'master'}

  options([
    parameters([
      [$class: 'CascadeChoiceParameter', 
        choiceType: 'PT_CHECKBOX', 
        description: 'Select Environment',
        filterLength: 1,
        filterable: false,
        name: 'Environment', 
        script: [
          $class: 'GroovyScript', 
          script: [
            classpath: [], 
            sandbox: false, 
            script: 
              'return[\'Development\',\'QA\',\'Staging\',\'Production\']'
          ]
        ]
      ]
    ])
  ])     
        
  parameters {
    booleanParam(name: 'CUSTOM_HOSTS', defaultValue: false, description: 'If need insert hosts manually')
    string(name: 'ANSIBLE_LIMITS', defaultValue: '', description: 'Field for ANSIBLE_LIMITS value')
  }

  stages {
    stage ('Some commands') {
      steps {
        shared_lib()
        sh "echo ${params.ANSIBLE_LIMITS}"
        sh "pwd"
        sh "ls -la"
        sh "git branch && git status"
      }
    }
    stage ('Stage with ANSIBLE_LIMITS') {
      steps {
        script {
          if (params.CUSTOM_HOSTS) {
            shared_lib.ansible_fake("${params.ANSIBLE_LIMITS}", "inventories/voshod/ppak")
          }
        }
      }
    }
    // stage ('Git checkout main branch') {
    //   steps {
    //     dir("$WORKSPACE") {
    //     checkout([$class: 'GitSCM',
    //               userRemoteConfigs: [[url: "git@github.com:lykarik/test_only_repo.git",
    //               credentialsId: 'jenkins-master-git-key']],
    //               branches: [[name: "main"]],
    //               extensions: [
    //                 [$class: 'CloneOption',
    //                   reference: "git@github.com:lykarik/test_only_repo.git",
    //                   shallow: true,
    //                   depth: "1"]
    //               ]
    //     ])
    //     }
    //   }
    // }
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
             echo "changes from" >> file_1
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
