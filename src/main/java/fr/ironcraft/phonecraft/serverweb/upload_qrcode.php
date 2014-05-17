
<?php
    $dirqrcode = "qrcodes";
    if(!file_exists($dirqrcode))
    {
        if(!mkdir($dirqrcode, 0777))
            echo "failed to create folder";
    }
    $path = dirname(__FILE__) . "/" . $dirqrcode;
    if (is_uploaded_file($_FILES['userfile']['tmp_name']))
    {
        echo "File ". $_FILES['userfile']['name'] ." uploaded successfully.<br />\n";
        move_uploaded_file($_FILES['userfile'] ['tmp_name'], $path . "/" . $_FILES['userfile'] ['name']);
    }
    else
    {
        echo "Possible file upload attack: <br />\n";
        echo "filename '". $_FILES['userfile']['tmp_name'] . "'.";
        print_r($_FILES);
    }
?>
