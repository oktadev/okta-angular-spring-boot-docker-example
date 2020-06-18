const PROXY_CONFIG = [
  {
    context: ['/user', '/api', '/oauth2', '/login'],
    target: 'http://localhost:8080',
    secure: false,
    logLevel: 'debug'
  }
]

module.exports = PROXY_CONFIG;
