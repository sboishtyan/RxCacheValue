## Library that can help you to cache in rx way

### todo list
1. Make implementations for Observable and Completable
2. Change dispose logic for multiply prefetch and get. Now if first Observer is dispose then the original observable will dispose
3. Change cache implementation to inner Cache interface from default java.util.Map
4. Concurrency usage support
#### work in progress...