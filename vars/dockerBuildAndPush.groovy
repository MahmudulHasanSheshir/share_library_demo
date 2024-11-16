
def call(Map params = [:]) {
    def credentialsId = params.credentialsId ?: error("dockerBuildAndPush: 'credentialsId' is required")
    def imageName = params.imageName ?: error("dockerBuildAndPush: 'imageName' is required")
    def dockerfile = params.get('dockerfile', 'Dockerfile')
    def buildNumber = params.get('buildNumber', env.BUILD_NUMBER ?: '1')
    def imageTag = "V-${buildNumber}"
    def registryUrl = params.get('registryUrl', '')

    // Authenticate with Docker registry and build/push image
    withDockerRegistry(credentialsId: credentialsId, url: registryUrl) {
        sh "docker build -t ${imageName}:${imageTag} -f ${dockerfile} ."
        sh "docker push ${imageName}:${imageTag}"
        sh "docker tag ${imageName}:${imageTag} ${imageName}:latest"
        sh "docker push ${imageName}:latest"
    }
}
