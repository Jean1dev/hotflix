import { IconButton, TextField } from "@mui/material";
import { useRef, useState } from "react";
import DeleteIcon from "@mui/icons-material/Delete";
import FileIcon from "@mui/icons-material/FileCopy";

interface Props {
  onAdd: (files: File) => void;
  onRemove: (file: File) => void;
  placeholder?: string;
}

export const InputFile: React.FC<Props> = ({
  onAdd,
  onRemove,
  placeholder,
}: Props) => {
  const [selectedFiles, setSelectedFiles] = useState<File>();
  const fileInputRef = useRef<HTMLInputElement>(null);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.item(0);
    if (!file) return;
    setSelectedFiles(file);
    onAdd(file);
  };

  const handleFileInput = () => {
    fileInputRef.current?.click();
  };

  const handleClear = () => {
    setSelectedFiles(undefined);
    if (selectedFiles) {
      onRemove(selectedFiles);
    }
  };

  return (
    <>
      <TextField
        type="text"
        placeholder={placeholder || "Select a file"}
        value={selectedFiles?.name || ""}
        InputProps={{
          readOnly: true,
          endAdornment: selectedFiles ? (
            <IconButton onClick={handleClear}>
              <DeleteIcon />
            </IconButton>
          ) : (
            <IconButton onClick={handleFileInput}>
              <FileIcon />
            </IconButton>
          ),
        }}
      />
      <input
        accept="*"
        type="file"
        id="inputFile"
        ref={fileInputRef}
        multiple={false}
        onChange={handleChange}
        style={{ display: "none" }}
      />
    </>
  );
};
