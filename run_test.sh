cd Tablut
ant compile
ant gui-server >/dev/null 2>/dev/null &
sleep 5
ant randomblack >/dev/null 2>/dev/null &
ant bimbe &