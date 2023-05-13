if [[ "$#" -lt 3 ]]; then
    echo "parameters color(BLACK|WHITE) timeout serveraddress"
    exit 1
fi
if [[ "$#" -eq 4 ]]; then
java -jar bimbe.jar "$1" "$3" "$2" "$4"

else
java -jar bimbe.jar "$1" "$3" "$2" 3
fi