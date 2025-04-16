<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF8">
  <link href="/styles.css" rel="stylesheet">
  <title>AUTHORS PAGE</title>
</head>
<body>
<div class="container">
  <div class="table-responsive">
    <h1>AUTHORS</h1>
    <table class="table table-striped">
      <thead>
        <tr>
          <th scope="col">Id</th>
          <th scope="col">Name</th>
        </tr>
      </thead>
      <tbody>
        <#list authors as author>
          <tr>
            <td><span>${author.id}</span></td>
            <td><span>${author.name}</span></td>
          </tr>
        </#list>
      </tbody>
    </table>
  </div>
</div>
</body>
</html>
