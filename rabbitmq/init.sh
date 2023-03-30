#!/bin/bash

( rabbitmqctl wait --timeout 60 $RABBITMQ_PID_FILE ; \

rabbitmqctl add_user $RABBITMQ_USER $RABBITMQ_PASSWORD 2>/dev/null ; \
rabbitmqctl set_user_tags $RABBITMQ_USER administrator ; \

rabbitmqctl add_vhost mini_autorizador_host; \
rabbitmqctl set_permissions -p mini_autorizador_host $RABBITMQ_USER ".*" ".*" ".*"; \

rabbitmqadmin declare exchange --vhost=mini_autorizador_host name=transaction_exchange type=direct -u $RABBITMQ_USER -p $RABBITMQ_PASSWORD ; \
rabbitmqadmin declare queue --vhost=mini_autorizador_host name=transaction_queue durable=true -u $RABBITMQ_USER -p $RABBITMQ_PASSWORD ; \
rabbitmqadmin  declare binding --vhost=mini_autorizador_host source=transaction_exchange destination_type=queue destination=transaction_queue routing_key=transaction_routing_key -u $RABBITMQ_USER -p $RABBITMQ_PASSWORD ; \
echo "*** Exchanges, Queues e Bindings criados ***" ) &

 rabbitmq-server $@