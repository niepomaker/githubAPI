# Github API
## Table of contents
* [Application description](#Application-description)
* [Exceptions](#Exceptions)
## Application description
The Github API application is used to display a list of all repositories for a given user. 
The app also shows all branches with name and last commit SHA 
in the follwoing format:
```yaml
[{
   {
    "Owner": "niepomaker",
    "Branches":
        {
        "Last commit": "a9b82a14f49db52e5169784c990e22771945d170",
        "Name": "master"
        },
        {
        "Last commit": "f30a6441a9d5b6c7ae435dad1a5ce3e96f4b8509",
        "Name": "main"
        }
    "Name": "alkosearch"
    }
}]
```

## Exceptions
There are 2 exceptions in the app's GitHub API:
* When the user does not exist:
```yaml
[
  {
    "Status": "404",
    "Message": "User does not exist!"
  }
]
```
* When an API consumer provide "Accept: application/xml" header: 
```yaml
[
  {
    "Status": "406",
    "Message": "Wrong header! Please provide application/json header. "
  }
]
```





