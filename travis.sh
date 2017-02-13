#!/bin/bash

export MAVEN_OPTS="-Xmx1G -Xms128m"
MAVEN_OPTIONS="-Dmaven.test.redirectTestOutputToFile=false -Dsurefire.useFile=false -B -e -V"

if [ "$TRAVIS_BRANCH" == "master" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ]; then
    echo 'Code push on master branch'

    # Fetch all commit history so that SonarQube has exact blame information
    # for issue auto-assignment
    # This command can fail with "fatal: --unshallow on a complete repository does not make sense"
    # if there are not enough commits in the Git repository (even if Travis executed git clone --depth 50).
    # For this reason errors are ignored with "|| true"
    git fetch --unshallow || true

    mvn org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar $MAVEN_OPTIONS
    
elif [ "$TRAVIS_PULL_REQUEST" != "false" ] && [ -n "${GITHUB_TOKEN:-}" ]; then
    echo 'Internal pull request from another branch to master'

    mvn org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar \
        $MAVEN_OPTIONS \
        -Dsource.skip=true \
        -Dsonar.analysis.mode=preview \
        -Dsonar.github.pullRequest=$TRAVIS_PULL_REQUEST \
        -Dsonar.github.repository=$TRAVIS_REPO_SLUG \
        -Dsonar.github.oauth=$GITHUB_TOKEN \

else
    echo 'push on other branches'
    mvn install $MAVEN_OPTIONS -Dsource.skip=true
fi
