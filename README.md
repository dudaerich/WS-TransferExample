WS-TransferExample
==================

Example program for WS-Transfer SOAP extension implemented in the Apache CXF project.

Scenario
--------

There are two separate servers. The first server called *ResourceFactoryServer* provides two services - *ResourceFactory* and *Resource*. The second server called *ResourceServer* contains one service *Resource*. Client program has reference only to the *ResourceFactory*. When the client sends request to create resource, the *ResourceFactory* makes decision where the resource will be stored. It can be stored locally in the first server or remotely in the second server. After creating the resource a reference parameters are returned. By this way the client can later *get*, *put* or *delete* the created resource.

The *ResourceFactory* supports two types of XML representations - student and teacher. If other type of XML representation is requested to create, the InvalidRepresentation fault is thrown. All elements in representations are optional. If they are not defined so the "Unspecified" value is set. The *ResourceFactory* generates an *UID* (University Identifier) for each accepted XML representation. The *UID* can't be modified by *put* request. If the method tries to change the *UID* the *PutDenied* fault is thrown.

Building
--------

First you should install *WS-Transfer* to your local maven repository. Sources of the whole *CXF Apache* project together with *WS-Transfer* you can find [here](https://github.com/dudaerich/cxf). Instructions for building *CXF Apache* project you can find [here](http://cxf.apache.org/building.html).

After that you have installed *WS-Transfer* you can run the *WS-TransferExample* program. Recommended way how to run the program is to open 3 terminals, where you run the *Client*, the *ResourceFactoryServer* and the *ResourceServer*.

```
mvn exec:java -pl client
```

```
mvn exec:java -pl resourcefactory
```

```
mvn exec:java -pl resource
```

Usage
-----

You can control the program from the *Client* terminal by entering commands. For more information about commands type

```
help
```

If you want to create some resource you should first load XML from local files. Either you can load all files from the directory

```
loadDir testFiles
```

or just one file

```
loadXML testFiles/createStudent.xml
```

After that you can print list of loaded XML files by typing

```
lsXML
1: testFiles/createStudent.xml
2: testFiles/putTeacher.xml
3: testFiles/createStudentWrong.xml
4: testFiles/putStudent.xml
5: testFiles/createTeacherPartial.xml
6: testFiles/createStudentPartial.xml
7: testFiles/createTeacherWrong.xml
8: testFiles/createTeacher.xml

```

**Important note**: The XML representations are referenced by a number showed by *lsXML*.

For example if you want to show the content of the XML *testFiles/createStudentPartial.xml* you can do it by command

```
showXML 6
<student xmlns="http://university.edu/student">
    <name>John</name>
    <birthdate>
        <day>14</day>
        <month>3</month>
        <year>1994</year>
    </birthdate>
</student>
```
Now you can create the resource using command

```
createRes 1
```

This command sends the request to the *ResourceFactory* for creating the resource from *testFiles/createStudent.xml*.

The reference parameters getting from response are stored and you can list them by command

```
lsRes
1: http://localhost:8080/ResourceStudents, b8f87a9f-a126-4ffa-8121-ba86085becd7
```

The resources are referenced by the same way as the XML representations. If you want to get the newly created resouce you just type

```
getRes 1
<student:student xmlns="http://university.edu/student"
    xmlns:ns2="http://www.w3.org/2005/08/addressing" xmlns:student="http://university.edu/student">
    <name>John</name>
    <surname>Smith</surname>
    <date>
        <day>14</day>
        <month>3</month>
        <year>1994</year>
    </date>
    <address>Street 21</address>
    <uid>1</uid>
</student:student>
```

For more information use *help* command.
