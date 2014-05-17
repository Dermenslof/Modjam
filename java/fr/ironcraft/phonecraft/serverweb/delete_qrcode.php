<?php
    $dirqrcode = "qrcodes";
    if (isset($_GET['name']) && file_exists($dirqrcode))
    {
        $path = dirname(__FILE__) . "/" . $dirqrcode;
        unlink($path . "/" . $_GET['name']);
        echo 'file is delete';
    }
?>
