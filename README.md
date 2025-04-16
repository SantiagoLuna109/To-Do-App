# To Do App 
## What do you need?
- OpenJDK Runtime Environment Corretto-23.0.2.7.1
- At least the Node version of: v22.14.0
- Have installed REACT
- Verify ig you have no problems with the next dependencies of React:
- {
  npm: '10.9.2',
  node: '22.14.0',
  acorn: '8.14.0',
  ada: '2.9.2',
  amaro: '0.3.0',
  ares: '1.34.4',
  brotli: '1.1.0',
  cjs_module_lexer: '1.4.1',
  cldr: '46.0',
  icu: '76.1',
  llhttp: '9.2.1',
  modules: '127',
  napi: '10',
  nbytes: '0.1.1',
  ncrypto: '0.0.1',
  nghttp2: '1.64.0',
  nghttp3: '1.6.0',
  ngtcp2: '1.10.0',
  openssl: '3.0.15+quic',
  simdjson: '3.10.1',
  simdutf: '6.0.3',
  sqlite: '3.47.2',
  tz: '2024b',
  undici: '6.21.1',
  unicode: '16.0',
  uv: '1.49.2',
  uvwasi: '0.0.21',
  v8: '12.4.254.21-node.22',
  zlib: '1.3.0.1-motley-82a5fec'

}
- And the next librearies:
- @testing-library/react@16.3.0 extraneous
- react@19.1.0 deduped
- react-dom@19.1.0 extraneous
- react@19.1.0 deduped
- react@19.1.0 extraneous
## How to use this App?
In a terminar copy the next:
```bash
git clone https://github.com/SantiagoLuna109/To-Do-App.git
```
Then:
```bash
cd To-Do-App/ 
```

### Run Front-End
If you are already in the To-Do-App/ Directory just move to the ToDo_Front/

```bash
 cd ToDo_Front/
```
and execute the next comands

```bash
 npm install
 npm run start
```
You shloud see something like this:
![image](https://github.com/user-attachments/assets/acf89474-1020-4248-be98-1abba37443a3)

and you could verify the page in the next url:
```bash
 http://localhost:8080/
```

and that is everything from the front end part! :D
Now move us to the back-end

### Run Back-End
If you are already in the To-Do-App/ Directory just move to the ToDo_Back/

```bash
 cd ToDo_Back/
```
and execute the next comand

```bash
 mvn spring-boot:run
```
and thats it! You now should see something like this:

![image](https://github.com/user-attachments/assets/521d67f2-841c-4939-a33d-63826979315e)

and you could verify the json format in the next url:
```bash
 http://localhost:9090/todos
```


