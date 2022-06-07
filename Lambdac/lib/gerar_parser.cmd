java -jar java-cup-11a.jar -expect 1 -parser Parser <..\src\lambdac\frontend\Parser.cup
move /Y *.java ..\src\lambdac\frontend\
@pause