module.exports = {
    preset: 'ts-jest',
    testEnvironment: 'jsdom', 
    setupFilesAfterEnv: ['<rootDir>/src/setupTest.ts'],
    transform: {
      '^.+\\.(js|jsx|ts|tsx)$': 'babel-jest',
    },
    moduleNameMapper: {
      '\\.(css|less|scss|sass)$': 'identity-obj-proxy'
    }
  };