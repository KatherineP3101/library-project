<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF8">
  <link href="/style.css" rel="stylesheet">
</head>
<body>
<div class="container">
  <div class="table-responsive">
    <h1>BOOKS</h1>
    <table class="table table-striped">
      <thead>
        <tr>
          <th scope="col">Id</th>
          <th scope="col">Title</th>
          <th scope="col">Author</th>
          <th scope="col">Genre</th>
        </tr>
      </thead>
      <tbody>
      <#list books as book>
        <tr>
          <td><span>${book.id}</span></td>
          <td><span>${book.name}</span></td>
          <td><span>${book.author}</span></td>
          <td><span>${book.genre}</span></td>
        </tr>
      </tbody>
      </#list>
    </table>
  </div>
</div>
</body>
</html>
