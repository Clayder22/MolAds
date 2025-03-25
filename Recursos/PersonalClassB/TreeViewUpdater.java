package Recursos.PersonalClassB;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreeViewUpdater {

    private WatchService watcher;
    private List<TreeItem<File>> rootItems = new ArrayList<>();

    public void monitorDirectoryChanges(TreeView<File> treeView, List<File> directoriesToMonitor) {
        try {
            watcher = FileSystems.getDefault().newWatchService();
            // Register directories to monitor
            for (File directory : directoriesToMonitor) {
                Paths.get(directory.getAbsolutePath()).register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
            }
            Thread thread = new Thread(() -> {
                while (true) {
                    try {
                        WatchKey key = watcher.take();
                        for (WatchEvent<?> event : key.pollEvents()) {
                            WatchEvent.Kind<?> kind = event.kind();
                            if (kind == StandardWatchEventKinds.ENTRY_CREATE || kind == StandardWatchEventKinds.ENTRY_DELETE) {
                                updateTreeView(treeView, rootItems, directoriesToMonitor);
                            }
                        }
                        key.reset();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.setDaemon(true);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateTreeView(TreeView<File> treeView, List<TreeItem<File>> rootItems, List<File> paths) {
        rootItems.clear();
        rootItems.addAll(createTreeViews(paths));
        treeView.getRoot().getChildren().setAll(rootItems);
    }

    private List<TreeItem<File>> createTreeViews(List<File> directories) {
        List<TreeItem<File>> rootItems = new ArrayList<>();
        for (File directory : directories) {
            TreeItem<File> rootItem = new TreeItem<>(directory);
            rootItem.setExpanded(true);
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file != null) { // Comprobar si el archivo no es nulo
                        TreeItem<File> item = new TreeItem<>(file);
                        rootItem.getChildren().add(item);
                        if (file.isDirectory()) {
                            item.getChildren().addAll(createTreeViews(Collections.singletonList(file)).get(0).getChildren());
                        }
                    }
                }
            }
            rootItems.add(rootItem);
        }
        return rootItems;
    }
}