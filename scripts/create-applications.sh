
REPLICATES_COUNT=2
SPINNAKER_CLIENT_URI=https://spin-gate.svc-stage.eng.vmware.com
export SPINNAKER_SESSION_ID=MzU1ZTA3ZmQtNmYwNS00NjllLWE3ZGEtNjBlNjQzNTg0NjU4

mkdir -p ~/.spinnaker/

jinja2 -D replicates=$REPLICATES_COUNT \
       -D spinnakerClientUri=$SPINNAKER_CLIENT_URI \
       ../jinja2/create-application.yml.tmpl > ~/.spinnaker/spinnaker-performance.yml

gradle perf