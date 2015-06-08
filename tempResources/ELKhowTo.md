

download ES, unzip, ./bin/elasticsearch

install _plugin/head

http://localhost:9200/_plugin/head/

do a PUT to localhost:9200/_template/waterTemplate_1 with the contents of waterMapping.json

download logstash, unzip it. create the file pythonUdp.conf run bin/logstash --verbose -f pythonUdp.conf

download kibana, unzip, ./bin/kibana

http://localhost:5601/

run __main__.py of graphDbWithAdapter.py which will shove data into logstash by running python graphDbWithAdapter

create a visualization with kibana

