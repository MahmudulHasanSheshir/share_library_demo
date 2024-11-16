def call(Map params = [:]) {
    def branch = params.get('branch', 'main')
    def credentialsId = params.get('credentialsId', 'github')
    def url = params.url

    if (!url) {
        error "gitCheckout: Git repository URL is required"
    }

    // Checkout the Git repository
    checkout([
        $class: 'GitSCM',
        branches: [[name: "*/${branch}"]],
        doGenerateSubmoduleConfigurations: false,
        extensions: [],
        submoduleCfg: [],
        userRemoteConfigs: [[
            credentialsId: credentialsId,
            url: url
        ]]
    ])
}
