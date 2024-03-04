echo '+---------------------------------------'
echo 'Startup.sh STARTED' 
echo '+---------------------------------------'
echo '' 
echo '+---------------------------------------'
echo 'npm list -g' 
echo '' 
npm list -g
echo '' 
echo '+---------------------------------------'
echo 'goto build folder and serve' 
echo '' 
cd ./reactui/build
serve .
echo '' 
echo 'End of Shell Script' 
echo '' 

