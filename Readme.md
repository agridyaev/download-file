Selenide download file test
===========================

**To run test:**

    mvn clean test -Dselenide.browser=chrome -Dselenide.timeout=55000 -DdownloadFolderPath=/home/agr/github/files/

Expected path: /home/agr/github/files/SpeedTest_16MB.md5<br/>
Actual path: build/reports/tests/pub/speed/SpeedTest_16MB.md5

**Output:**

    Folder "/home/agr/github/files/" exists: true
    Downloaded file path: /home/agr/github/java/download-file/build/reports/tests/pub/speed/SpeedTest_16MB.md5
    Downloaded file size: 49 bytes